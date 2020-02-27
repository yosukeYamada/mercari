package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.User;

@Repository
public class UserRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<User> USER_ROW_MAPPER = (rs, i) -> {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("name"));
		user.setPassword(rs.getString("password"));
		user.setUuid(rs.getString("uuid"));
		user.setAuthority(rs.getInt("authority"));
		user.setRegisterDate(rs.getDate("register_date"));
		return user;
	};

	public void insertFlush(User user) {
		String sql = "INSER INTO users (password,uuid,authority,register_date) VALUES(:password,:uuid,:authority,:registerDate)";
		SqlParameterSource param = new MapSqlParameterSource().addValue("password", user.getPassword())
				.addValue("uuid", user.getUuid()).addValue("authority", user.getAuthority())
				.addValue("register_date", user.getRegisterDate());
		template.update(sql, param);

	}

	public void insert(User user) {
		String sql = "INSER INTO users (name,password,uuid,authority,register_date) VALUES(:name,:password,:uuid,:authority,:registerDate)";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", user.getName())
				.addValue("password", user.getPassword()).addValue("uuid", user.getUuid())
				.addValue("authority", user.getAuthority()).addValue("register_date", user.getRegisterDate());
		template.update(sql, param);

	}

	public User load(String name) {
		String sql = "SELECT id,name,password,authority,register_date FROM users WHERE name=:name";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", name);

		try {
			return template.queryForObject(sql, param, USER_ROW_MAPPER);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}

	}
}
