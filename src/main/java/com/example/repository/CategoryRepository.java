package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Category;

@Repository
public class CategoryRepository {

	private static RowMapper<Category> CATEGORY_ROW_MAPPER = (rs, i) -> {
		Category category = new Category();
		category.setId(rs.getInt("id"));
		category.setName(rs.getString("name"));
		category.setParent(rs.getInt("parent_id"));
		category.setName_all(rs.getString("name_all"));
		return category;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	public List<Category> findByParentIsNull() {
		String sql = "SELECT id,parent_id,name,name_all FROM category WHERE parent_id is null";
		List<Category> parentCategoryList = template.query(sql, CATEGORY_ROW_MAPPER);
		return parentCategoryList;
	}

	public List<Category> findByParent(Integer parent) {
		String sql = "SELECT id,parent_id,name,name_all FROM category WHERE parent_id = :parent";
		SqlParameterSource param = new MapSqlParameterSource().addValue("parent", parent);
		List<Category> childCategoryList = template.query(sql, param, CATEGORY_ROW_MAPPER);
		return childCategoryList;
	}
	
	public List<Category> findByParentAndChildCategoryName(Integer parent,String childCategoryName){
		String sql = "SELECT id,parent_id,name,name_all FROM category WHERE parent_id = :parent AND SPLIT_PART(name_all,'/',2) = :childCategoryName";
		SqlParameterSource param = new MapSqlParameterSource().addValue("parent", parent).addValue("childCategoryName", childCategoryName);
		List<Category> grandCategoryList = template.query(sql, param, CATEGORY_ROW_MAPPER);
		return grandCategoryList;		
	}

}
