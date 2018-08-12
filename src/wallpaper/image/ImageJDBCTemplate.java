package wallpaper.image;

import java.util.Date;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class ImageJDBCTemplate {
	protected DataSource dataSource;
	protected JdbcTemplate jdbcTemplateObject;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	protected int create(String id, String name, String user, Date date) {
		String SQL = "insert into image (id, name, user, date) values (?, ?, ?, ?)";
		int i = jdbcTemplateObject.update(SQL, id, name, user, date);
		return i;
	}
}
