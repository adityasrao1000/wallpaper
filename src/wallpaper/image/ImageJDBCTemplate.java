package wallpaper.image;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

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

	public List<ImageDetails> listImages(String id) {
		String SQL = "select * from image where id = ?";
		List<ImageDetails> images = jdbcTemplateObject.query(SQL, new Object[] { id }, new ImageMapper());
		return images;
	}

	private static class ImageMapper implements RowMapper<ImageDetails> {
		public ImageDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
			ImageDetails image = new ImageDetails();
			image.setId(rs.getString("id"));
			image.setName(rs.getString("name"));
			image.setUser(rs.getString("user"));
			image.setDate(rs.getDate("date"));
			return image;
		}
	}
}
