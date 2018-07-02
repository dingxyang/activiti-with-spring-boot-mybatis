package com.dxy;

import com.dxy.dto.ApplicantDto;
import com.dxy.mapper.ApplicantMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.subethamail.wiser.Wiser;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ActivitiWithMybatisApplication.class)
public class ActivitiWithMybatisApplicationTests {

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private ApplicantMapper applicantMapper;

	private Wiser wiser;


	@Test
	public void contextLoads() {
		// Create test applicant
		ApplicantDto applicant = new ApplicantDto("John Doe", "john@activiti.org", "12344");
		applicantMapper.insert(applicant);

		// Start process instance
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("applicant", applicant);
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("hireProcessWithJpa", variables);

		// First, the 'phone interview' should be active
		Task task = taskService.createTaskQuery()
				.processInstanceId(processInstance.getId())
				.taskCandidateGroup("dev-managers")
				.singleResult();
		Assert.assertEquals("Telephone interview", task.getName());

		// Completing the phone interview with success should trigger two new tasks
		Map<String, Object> taskVariables = new HashMap<String, Object>();
		taskVariables.put("telephoneInterviewOutcome", true);
		taskService.complete(task.getId(), taskVariables);

		List<Task> tasks = taskService.createTaskQuery()
				.processInstanceId(processInstance.getId())
				.orderByTaskName().asc()
				.list();
		Assert.assertEquals(2, tasks.size());
		Assert.assertEquals("Financial negotiation", tasks.get(0).getName());
		Assert.assertEquals("Tech interview", tasks.get(1).getName());

		// Completing both should wrap up the subprocess, send out the 'welcome mail' and end the process instance
		taskVariables = new HashMap<String, Object>();
		taskVariables.put("techOk", true);
		taskService.complete(tasks.get(0).getId(), taskVariables);

		taskVariables = new HashMap<String, Object>();
		taskVariables.put("financialOk", true);
		taskService.complete(tasks.get(1).getId(), taskVariables);

		// Verify email
		Assert.assertEquals(1, wiser.getMessages().size());

		// Verify process completed
		Assert.assertEquals(1, historyService.createHistoricProcessInstanceQuery().finished().count());
	}

}
