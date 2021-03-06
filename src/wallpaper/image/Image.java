package wallpaper.image;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import wallpaper.common.Social;
import wallpaper.singleton.GsonSingleton;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/image")
public class Image {

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> get(@PathVariable("id") String id) {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-jdbc.xml");
		ImageJDBCTemplate imageJDBCTemplate = (ImageJDBCTemplate) context.getBean("ImageJDBCTemplate");
		List<ImageDetails> result = imageJDBCTemplate.listImages(id);
		if (context != null) {
			if (context instanceof ApplicationContext) {
				((AbstractApplicationContext) context).close();
			}
		}

		if (result != null) {
			ImageDetails record = result.get(0);
			if (record != null) {
				System.out.println();
				return new ResponseEntity<String>(record.toJSON(), HttpStatus.OK);
			}
			return new ResponseEntity<String>(id, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<String>(id, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<String> put(@PathVariable("id") String name, @RequestParam("file") MultipartFile file,
			@RequestHeader("token") String token) {

		String fileType = file.getContentType();
		String[] types = { "image/gif", "image/jpeg", "image/jpg", "image/webp", "image/png" };
		if (!Arrays.asList(types).contains(fileType)) {
			return new ResponseEntity<String>("Invalid content, please upload an image", HttpStatus.BAD_REQUEST);
		}

		Gson gson = GsonSingleton.getGson();
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = null;
		String url = null;
		Token auth_token = null;

		/*
		 * map the token onto the Token class, return bad request if the token is
		 * invalid
		 */
		try {
			auth_token = gson.fromJson(token, Token.class);
		} catch (Exception e) {
			return new ResponseEntity<String>("Upload failed", HttpStatus.BAD_REQUEST);
		}

		if (auth_token.getProvider().equalsIgnoreCase(Social.FACEBOOK.value())) {
			url = "https://graph.facebook.com/me?fields=name,email&access_token";
		} else {
			url = "https://www.googleapis.com/oauth2/v1/tokeninfo?access_token";
		}

		try {
			response = restTemplate.getForEntity(url + "=" + auth_token.getToken(), String.class);
		} catch (HttpClientErrorException e) {
			return new ResponseEntity<String>("Upload failed", HttpStatus.UNAUTHORIZED);
		}

		if (response != null) {
			Type listType = new TypeToken<HashMap<String, String>>() {
			}.getType();
			Map<String, String> list = gson.fromJson(response.getBody(), listType);
			String email = list.get("email");

			ApplicationContext context = new ClassPathXmlApplicationContext("spring-jdbc.xml");
			ImageJDBCTemplate imageJDBCTemplate = (ImageJDBCTemplate) context.getBean("ImageJDBCTemplate");
			int result = imageJDBCTemplate.create(getSaltString(60), name, email, new Date());
			if (context != null) {
				if (context instanceof ApplicationContext) {
					((AbstractApplicationContext) context).close();
				}
			}

			if (result <= 0) {
				return new ResponseEntity<String>("Upload failed, please try again", HttpStatus.INTERNAL_SERVER_ERROR);
			}

			final Logger log = Logger.getLogger(Image.class.getName());

			File folder = new File("d:\\wallpapers\\" + email + "\\");
			if (!folder.exists()) {
				folder.mkdirs();
			}

			File image_file = new File("d:\\wallpapers\\" + email + "\\" + name + ".png");
			if (image_file.exists()) {
				return new ResponseEntity<String>(name + " already exists", HttpStatus.CONFLICT);
			}

			try (FileOutputStream fout = new FileOutputStream(image_file)) {

				fout.write(file.getBytes());

			} catch (IOException e1) {
				e1.printStackTrace();
				return new ResponseEntity<String>("Upload failed", HttpStatus.INTERNAL_SERVER_ERROR);
			}

			log.info("image uploaded successfully");
			return new ResponseEntity<String>("file uploaded successfully", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Upload failed", HttpStatus.UNAUTHORIZED);
		}
	}

	/**
	 * <h1>Generates a random String of whose length is determined by the value of
	 * the parameter passed</h1>
	 * 
	 * @param of type int, it specifies the length of the random String that is to
	 *           be generated
	 * @return String of length = to the parameter
	 */
	protected static String getSaltString(int len) {
		String SALTCHARS = "abcdefghijklmnopqrstuvwxyz1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < len) {
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;

	}
}
