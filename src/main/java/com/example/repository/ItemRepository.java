package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Category;
import com.example.domain.Item;

@Repository
public class ItemRepository {

	private static RowMapper<Item> ITEM_ROW_MAPPER = (rs, i) -> {

		Item item = new Item();
		Category category = new Category();
		category.setId(rs.getInt("category_id"));
		category.setName(rs.getString("category_name"));
		category.setParent(rs.getInt("category_parent"));
		category.setName_all(rs.getString("category_name_all"));
		item.setId(rs.getInt("item_id"));
		item.setCondition(rs.getInt("item_condition"));
		item.setDescription(rs.getString("item_description"));
		item.setName(rs.getString("item_name"));
		item.setPrice(rs.getInt("item_price"));
		item.setShipping(rs.getInt("item_shipping"));
		item.setCategory(category);
		return item;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	public List<Item> findOnly30Items(String itemName,String brandName,Integer offset) {
		
		String x="i.name ILIKE ";
		String y="";
		String z="i.brand_name ILIKE ";
		String v="";
		if(itemName==null || ("").equals(itemName)) {
			itemName="";
			x = "("+x;
			y ="or i.name is null)";
		}
		String name = "%" + itemName + "%";
		if(brandName==null || ("").equals(brandName)) {
			brandName="";
			z="("+z;
			v="or i.brand_name is null)";
		}
		String brand = "%" + brandName + "%";
		String sql = "SELECT c.id AS category_id,"
				+ "c.parent_id AS category_parent,"
				+ "c.name_all AS category_name_all,"
				+ "i.id AS item_id,"
				+ "i.item_description AS item_description,"
				+ "i.shipping AS item_shipping, "
				+ "i.name AS item_name,"
				+ "i.price AS item_price,"
				+ "c.name_all AS category_name,"
				+ "i.brand_name AS item_brand,"
				+ "i.item_condition_id AS item_condition "
				+ "FROM items AS i "
				+ "JOIN category AS c "
				+ "ON i.category_id = c.id "
				+ "WHERE "+x+":itemName "+y
				+ "AND "+z+":brandName "+v
				+ "ORDER BY i.id ASC "
				+ "LIMIT 30 OFFSET :offset";
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("offset", offset).addValue("itemName", name).addValue("brandName", brand);
		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
		return itemList;
	}

	public Integer countForAllItems(String itemName,String brandName) {
		String x="i.name ILIKE ";
		String y="";
		String z="i.brand_name ILIKE ";
		String v="";
		if(itemName==null || ("").equals(itemName)) {
			itemName="";
			x = "("+x;
			y ="or i.name is null)";
		}
		String name = "%" + itemName + "%";
		if(brandName==null || ("").equals(brandName)) {
			brandName="";
			z="("+z;
			v="or i.brand_name is null)";
		}
		String brand = "%" + brandName + "%";
		String sql = "SELECT COUNT(*) "
				+ "FROM items AS i JOIN category AS c "
				+ "ON i.category_id = c.id "
				+ "WHERE "+x+":itemName "+y
				+ "AND "+z+":brandName "+v;
		SqlParameterSource param = new MapSqlParameterSource().addValue("itemName", name).addValue("brandName", brand);
		Integer count = template.queryForObject(sql, param, Integer.class);
		return count;
	}

	public List<Item> findByItemNameAndGrandChildCategoryNameOnly30Items(String itemName, Integer grandChildCategoryId,
			Integer childCategoryId, Integer parentCategoryId, String brandName, Integer offset) {
		String x="i.name ILIKE ";
		String y="";
		String z="i.brand_name ILIKE ";
		String v="";
		if(itemName==null || ("").equals(itemName)) {
			itemName="";
			x = "("+x;
			y ="or i.name is null)";
		}
		String name = "%" + itemName + "%";
		if(brandName==null || ("").equals(brandName)) {
			brandName="";
			z="("+z;
			v="or i.brand_name is null)";
		}
		String brand = "%" + brandName + "%";
		String sql = "SELECT c.id AS category_id,"
				+ "c.parent_id AS category_parent,"
				+ "cc.parent_id AS parent_category_id,"
				+ "c.name_all AS category_name_all,"
				+ "i.id AS item_id,"
				+ "i.item_description AS item_description,"
				+ "i.shipping AS item_shipping,"
				+ "i.name AS item_name,"
				+ "i.price AS item_price,"
				+ "c.name_all AS category_name,"
				+ "i.brand_name AS item_brand,"
				+ "i.item_condition_id AS item_condition "
				+ "FROM items AS i "
				+ "JOIN category AS c ON i.category_id = c.id "
				+ "JOIN category AS cc ON c.parent_id = cc.id "
				+ "WHERE category_id = :grandChildCategoryId "
				+ "AND c.parent_id = :childCategoryId "
				+ "AND cc.parent_id = :parentCategoryId "
				+ "AND "+x+":itemName "+y
				+ "AND "+z+":brandName "+v
				+ "ORDER BY i.id ASC "
				+ "LIMIT 30 OFFSET :offset";
		SqlParameterSource param = new MapSqlParameterSource().addValue("grandChildCategoryId", grandChildCategoryId)
				.addValue("childCategoryId", childCategoryId).addValue("parentCategoryId", parentCategoryId)
				.addValue("itemName", name).addValue("brandName", brand).addValue("offset", offset);
		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
		return itemList;
	}
	
	/**
	 * 小カテゴリー検索を行った際のデータ数を検索するメソッド.
	 * 
	 * @param itemName
	 * @param grandChildCategoryId
	 * @param childCategoryId
	 * @param parentCategoryId
	 * @param brandName
	 * @return
	 */
	public Integer countForUsingGrandChildCategory(String itemName, Integer grandChildCategoryId,
			Integer childCategoryId, Integer parentCategoryId, String brandName) {
		String x="i.name ILIKE ";
		String y="";
		String z="i.brand_name ILIKE ";
		String v="";
		if(itemName==null || ("").equals(itemName)) {
			itemName="";
			x = "("+x;
			y ="or i.name is null)";
		}
		String name = "%" + itemName + "%";
		if(brandName==null || ("").equals(brandName)) {
			brandName="";
			z="("+z;
			v="or i.brand_name is null)";
		}
		String brand = "%" + brandName + "%";
		String sql = "SELECT count(*) "
				+ "FROM items AS i JOIN category AS c ON i.category_id = c.id "
				+ "JOIN category AS cc ON c.parent_id = cc.id "
				+ "WHERE category_id = :grandChildCategoryId "
				+ "AND c.parent_id = :childCategoryId "
				+ "AND cc.parent_id = :parentCategoryId "
				+ "AND "+x+":itemName "+y
				+ "AND "+z+":brandName "+v;
		SqlParameterSource param = new MapSqlParameterSource().addValue("grandChildCategoryId", grandChildCategoryId)
				.addValue("childCategoryId", childCategoryId).addValue("parentCategoryId", parentCategoryId)
				.addValue("itemName", name).addValue("brandName", brand);
		Integer count = template.queryForObject(sql, param, Integer.class);
		return count;
	}

	public List<Item> findByItemNameAndChildCategoryNameOnly30Items(String itemName, Integer childCategoryId,
			Integer parentCategoryId, String brandName, Integer offset) {
		String x="i.name ILIKE ";
		String y="";
		String z="i.brand_name ILIKE ";
		String v="";
		if(itemName==null || ("").equals(itemName)) {
			itemName="";
			x = "("+x;
			y ="or i.name is null)";
		}
		String name = "%" + itemName + "%";
		if(brandName==null || ("").equals(brandName)) {
			brandName="";
			z="("+z;
			v="or i.brand_name is null)";
		}
		String brand = "%" + brandName + "%";
		String sql = "SELECT c.id AS category_id,"
				+ "c.parent_id AS category_parent,"
				+ "cc.parent_id AS parent_category_id,"
				+ "c.name_all AS category_name_all,"
				+ "i.id AS item_id,"
				+ "i.item_description AS item_description,"
				+ "i.shipping AS item_shipping,"
				+ "i.name AS item_name,"
				+ "i.price AS item_price,"
				+ "c.name_all AS category_name,"
				+ "i.brand_name AS item_brand,"
				+ "i.item_condition_id AS item_condition "
				+ "FROM items AS i JOIN category AS c "
				+ "ON i.category_id = c.id "
				+ "JOIN category AS cc "
				+ "ON c.parent_id = cc.id "
				+ "WHERE c.parent_id = :childCategoryId "
				+ "AND cc.parent_id = :parentCategoryId "
				+ "AND "+x+":itemName "+y
				+ "AND "+z+":brandName "+v
				+ "ORDER BY i.id ASC LIMIT 30 OFFSET :offset";

		SqlParameterSource param = new MapSqlParameterSource().addValue("childCategoryId", childCategoryId)
				.addValue("parentCategoryId", parentCategoryId).addValue("itemName", name).addValue("brandName", brand)
				.addValue("offset", offset);
		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
		return itemList;
	}
	
	/**
	 * 中カテゴリ-検索を行った際のデータ検索を行うメソッド.
	 * 
	 * @param itemName
	 * @param childCategoryId
	 * @param parentCategoryId
	 * @param brandName
	 * @return
	 */
	public Integer countForUsingChildCategory(String itemName, Integer childCategoryId,Integer parentCategoryId, String brandName) {
		String x="i.name ILIKE ";
		String y="";
		String z="i.brand_name ILIKE ";
		String v="";
		if(itemName==null || ("").equals(itemName)) {
			itemName="";
			x = "("+x;
			y ="or i.name is null)";
		}
		String name = "%" + itemName + "%";
		if(brandName==null || ("").equals(brandName)) {
			brandName="";
			z="("+z;
			v="or i.brand_name is null)";
		}
		String brand = "%" + brandName + "%";
		String sql = "SELECT count(*) "
				+ "FROM items AS i JOIN category AS c "
				+ "ON i.category_id = c.id "
				+ "JOIN category AS cc "
				+ "ON c.parent_id = cc.id "
				+ "WHERE c.parent_id = :childCategoryId "
				+ "AND cc.parent_id = :parentCategoryId "
				+ "AND "+x+":itemName "+y
				+ "AND "+z+":brandName "+v;
		SqlParameterSource param = new MapSqlParameterSource().addValue("childCategoryId", childCategoryId)
				.addValue("parentCategoryId", parentCategoryId).addValue("itemName", name).addValue("brandName", brand);
		Integer count = template.queryForObject(sql, param, Integer.class);
		return count;
	}
	

	/**
	 * アイテム名＋カテゴリー名＋ブランド名で検索を行うメソッド
	 * 
	 * @param itemName
	 * @param parentCategoryId
	 * @param brandName
	 * @param offset
	 * @return
	 */
	public List<Item> findByItemNameAndCategoryNameOnly30Items(String itemName, Integer parentCategoryId, String brandName,
			Integer offset) {
		String x="i.name ILIKE ";
		String y="";
		String z="i.brand_name ILIKE ";
		String v="";
		if(itemName==null || ("").equals(itemName)) {
			itemName="";
			x = "("+x;
			y ="or i.name is null)";
		}
		String name = "%" + itemName + "%";
		if(brandName==null || ("").equals(brandName)) {
			brandName="";
			z="("+z;
			v="or i.brand_name is null)";
		}
		String brand = "%" + brandName + "%";
		String sql = "SELECT c.id AS category_id,"
				+ "c.parent_id AS category_parent,"
				+ "cc.parent_id AS parent_category_id,"
				+ "c.name_all AS category_name_all,"
				+ "i.id AS item_id,"
				+ "i.item_description AS item_description,"
				+ "i.shipping AS item_shipping,"
				+ "i.name AS item_name,"
				+ "i.price AS item_price,"
				+ "c.name_all AS category_name,"
				+ "i.brand_name AS item_brand,"
				+ "i.item_condition_id AS item_condition "
				+ "FROM items AS i JOIN category AS c "
				+ "ON i.category_id = c.id "
				+ "JOIN category AS cc "
				+ "ON c.parent_id = cc.id "
				+ "WHERE cc.parent_id = :parentCategoryId "
				+ "AND "+x+":itemName "+y
				+ "AND "+z+":brandName "+v
				+ "ORDER BY i.id ASC "
				+ "LIMIT 30 OFFSET :offset";
		SqlParameterSource param = new MapSqlParameterSource().addValue("parentCategoryId", parentCategoryId)
				.addValue("itemName", name).addValue("brandName", brand).addValue("offset", offset);
		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
		return itemList;
	}
	
	/**
	 * 大カテゴリ-検索を行った際のトータルデータ数を検索するメソッド.
	 * 
	 * @param itemName
	 * @param childCategoryId
	 * @param parentCategoryId
	 * @param brandName
	 * @return
	 */
	public Integer countForCategory(String itemName, Integer childCategoryId,Integer parentCategoryId, String brandName) {
		String x="i.name ILIKE ";
		String y="";
		String z="i.brand_name ILIKE ";
		String v="";
		if(itemName==null || ("").equals(itemName)) {
			itemName="";
			x = "("+x;
			y ="or i.name is null)";
		}
		String name = "%" + itemName + "%";
		if(brandName==null || ("").equals(brandName)) {
			brandName="";
			z="("+z;
			v="or i.brand_name is null)";
		}
		String brand = "%" + brandName + "%";
		String sql = "SELECT count(*) "
				+ "FROM items AS i JOIN category AS c "
				+ "ON i.category_id = c.id "
				+ "JOIN category AS cc "
				+ "ON c.parent_id = cc.id "
				+ "WHERE cc.parent_id = :parentCategoryId "
				+ "AND "+x+":itemName "+y
				+ "AND "+z+":brandName "+v;
		SqlParameterSource param = new MapSqlParameterSource().addValue("parentCategoryId", parentCategoryId)
				.addValue("itemName", name).addValue("brandName", brand);
		Integer count = template.queryForObject(sql, param, Integer.class);
		return count;
	}

}
