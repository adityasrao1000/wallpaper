package wallpaper.error.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/error")
public class ErrorApi {

	@RequestMapping(value = "/404", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> get404() {
		return new ResponseEntity<String>("not found", HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/401", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> get401() {
		return new ResponseEntity<String>("not found", HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(value = "/500", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> get500() {
		return new ResponseEntity<String>("not found", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
