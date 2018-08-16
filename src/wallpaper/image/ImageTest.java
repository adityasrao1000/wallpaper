package wallpaper.image;

import static org.junit.Assert.assertEquals;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;

public class ImageTest {

	ImageDetails id = null;
	
	@Before
	public void Init() {
		id = new ImageDetails();
		id.setId("asdfg123");
		id.setName("asdf");
		id.setUser("john doe");
		id.setDate(new Date());
	}
	
	@Test
	public void SaltStringLength() {
		assertEquals(Image.getSaltString(60).length(), 60);
	}
	
	@Test
	public void ImageDetails() {
		assertEquals(id.getName(), "asdf");
	}
}
