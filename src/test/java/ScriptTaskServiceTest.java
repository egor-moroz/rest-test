import com.rest.example.service.ScriptTaskService;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testConfig-root.xml"})
@WebAppConfiguration
public class ScriptTaskServiceTest {

    @Autowired
    ScriptTaskService taskService;

    @Test
    public void deleteScriptTaskTest() throws InterruptedException {
        int threadCount = Thread.activeCount();
        Integer taskId = taskService.addScriptTask("while(true){}");
        assertEquals(threadCount + 1, Thread.activeCount());
        taskService.deleteScriptTask(taskId);
        Thread.sleep(10000);
        assertEquals(threadCount, Thread.activeCount());
    }
}
