package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Category;
import com.example.domain.Item;
import com.example.form.SearchForm;
import com.example.repository.CategoryRepository;
import com.example.repository.ItemRepository;

@Service
public class ItemListService {

	private static final Integer ONE_PAGE_VIEW = 30;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private CategoryRepository categoryReposiory;


	/**
	 * ページングに使うオフセットを作るメソッド
	 * 
	 * @param nowPage 現在のページ
	 * @return offsetの値
	 */
	public Integer makeOffset(Integer nowPage) {
		Integer offset;
		offset = ONE_PAGE_VIEW * (nowPage - 1);
		return offset;
	}


	/**
	 * ページングの最後の数字を作るメソッド
	 * 
	 * @param searchForm 検索フォームからのリクエストパラメータ
	 * @return ページングの最後の数字
	 */
	public Integer getLastPageNumber(SearchForm searchForm) {
		Integer totalItemCount = searchTotalDataCount(searchForm);
		Integer lastPageNumber;
		if (totalItemCount % ONE_PAGE_VIEW == 0) {
			lastPageNumber = totalItemCount / ONE_PAGE_VIEW;
		} else {
			lastPageNumber = totalItemCount / ONE_PAGE_VIEW + 1;
		}
		return lastPageNumber;
	}

	/**
	 * 大カテゴリ-を抽出するメソッド.
	 * 
	 * @return 大カテゴリ-リスト
	 */
	public List<Category> getParentCategoryList() {
		List<Category> categoryList = new ArrayList<>();
		categoryList = categoryReposiory.findByParentIsNull();
		return categoryList;
	}

	/**
	 * 中カテゴリ-を抽出するメソッド.
	 * 
	 * @param parent 大カテゴリ-
	 * @return 中カテゴリ-リスト
	 */
	public List<Category> getChildCategoryList(Integer parent) {
		List<Category> childCategoryList = categoryReposiory.findByParent(parent);
		return childCategoryList;
	}
	
	/**
	 * 小カテゴリーを抽出するメソッド.
	 * 
	 * @param parent 大カテゴリ-名
	 * @param childCategoryName 中カテゴリ-名
	 * @return 小カテゴリーリスト
	 */
	public List<Category> getGrandCategoryList(Integer parent, String childCategoryName) {
		List<Category> childCategoryList = categoryReposiory.findByParentAndChildCategoryName(parent, childCategoryName);
		return childCategoryList;
	}
	
	/**
	 * カテゴリー & キーワード & ページ番号 から該当するアイテムを検索するメソッド.
	 * 
	 * @param searchForm 検索に使うリクエストパラメータ
	 * @param nowPage 現在のページ番号
	 * @return 検索結果
	 */
	public List<Item> searchItemList(SearchForm searchForm,Integer nowPage){
		String itemName = searchForm.getItemName();
		Integer grandChildCategoryId = searchForm.getIntGrandChildCategoryName();
		Integer childCategoryId = searchForm.getIntChildParentCategoryName();
		Integer parentCategoryId = searchForm.getIntParentCategoryName();
		String brandName = searchForm.getBrand();
		Integer offset = makeOffset(nowPage);
		List<Item> itemList = new ArrayList<>();
		if(grandChildCategoryId != null) {
			itemList = itemRepository.findByItemNameAndGrandChildCategoryNameOnly30Items(itemName, grandChildCategoryId, childCategoryId, parentCategoryId, brandName, offset);
		}else if(childCategoryId != null) {
			itemList = itemRepository.findByItemNameAndChildCategoryNameOnly30Items(itemName, childCategoryId, parentCategoryId, brandName, offset);
		}else if(parentCategoryId != null){
			itemList = itemRepository.findByItemNameAndCategoryNameOnly30Items(itemName, parentCategoryId, brandName, offset);
		}else {
			itemList = itemRepository.findOnly30Items(itemName, brandName, offset);
		}
		return itemList;
	}
	
	/**
	 * 検索のヒット数を返すメソッド.
	 * 
	 * @param searchForm 検索のリクエストパラメータ
	 * @return ヒット数
	 */
	public Integer searchTotalDataCount(SearchForm searchForm) {
		String itemName = searchForm.getItemName();
		Integer grandChildCategoryId = searchForm.getIntGrandChildCategoryName();
		Integer childCategoryId = searchForm.getIntChildParentCategoryName();
		Integer parentCategoryId = searchForm.getIntParentCategoryName();
		String brandName = searchForm.getBrand();
		Integer count = null;
		if(grandChildCategoryId != null) {
			count = itemRepository.countForUsingGrandChildCategory(itemName, grandChildCategoryId, childCategoryId, parentCategoryId, brandName);
		}else if(childCategoryId != null) {
			count = itemRepository.countForUsingChildCategory(itemName, childCategoryId, parentCategoryId, brandName);
		}else if(parentCategoryId != null){
			count = itemRepository.countForCategory(itemName, childCategoryId, parentCategoryId, brandName);
		}else {
			count = itemRepository.countForAllItems(itemName, brandName);
		}
		return count;
	}
	
	
	
}