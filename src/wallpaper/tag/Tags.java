package wallpaper.tag;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/tags")
public class Tags {

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> getTag(@PathVariable("id") String id) {
		final Logger log = Logger.getLogger(Tags.class.getName());
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-jdbc.xml");

		StudentJDBCTemplate studentJDBCTemplate = (StudentJDBCTemplate) context.getBean("studentJDBCTemplate");
		System.out.println("------Listing Multiple Records--------");

		List<Student> students = studentJDBCTemplate.listStudents();
		
		String json = new Gson().toJson(students);

		log.info("tag requested");
		((AbstractApplicationContext) context).close();
		return new ResponseEntity<String>(json, HttpStatus.OK);

	}
}