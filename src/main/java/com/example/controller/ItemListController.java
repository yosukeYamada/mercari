package com.example.controller;

import java.util.List;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.domain.Category;
import com.example.domain.Item;
import com.example.form.SearchForm;
import com.example.service.ItemListService;

@Controller
//@SessionAttributes(value = { "SearchForm" }) 
@SessionAttributes(types = SearchForm.class)
@RequestMapping("/itemList")
public class ItemListController {

	@Autowired
	private ItemListService itemListService;

	@Autowired
	private HttpSession session;

	@ModelAttribute
	public SearchForm setUpSearchForm() {
		return new SearchForm();
	}

	/**
	 * 商品一覧を表示するメソッド.
	 * 
	 * @param nowPage 現在のページ
	 * @param searchForm リクエストパラメータ
	 * @param model リクエストスコープ
	 * @return 商品一覧画面に遷移
	 */
	@RequestMapping("")
	public String index(Integer nowPage, SearchForm searchForm, Model model) {
		if (nowPage == null) {
			nowPage = 1;
//			if(searchForm.getItemName()==null) {
//				searchForm.setItemName("");
//			}
//			if(searchForm.getBrand()==null) {
//				searchForm.setBrand("");
//			}
			Integer lastPageNumber = itemListService.getLastPageNumber(searchForm);
			session.setAttribute("lastPageNumber", lastPageNumber); //最後のぺージ
			List<Category> parentCategoryList = itemListService.getParentCategoryList();
			session.setAttribute("parentCategoryList", parentCategoryList); //大カテゴリ-リスト
		}
		List<Item> itemList = itemListService.searchItemList(searchForm, nowPage);
		model.addAttribute("itemList", itemList); //表示するページリスト
		model.addAttribute("nowPage", nowPage); // 現在のページ
		return "list";
	}

	/**
	 * 中カテゴリ-リストを取得するメソッド.
	 * 
	 * @param parent 大カテゴリーId
	 * @return 中カテゴリ-リスト
	 */
	@RequestMapping("/findByChildCategoryName")
	@ResponseBody
	public List<Category> getChildCategoryList(Integer parent) {
		List<Category> childCategoryList = itemListService.getChildCategoryList(parent);
		return childCategoryList;
	}

	/**
	 * 小カテゴリーリストを取得するメソッド.
	 * 
	 * @param parent 大カテゴリ-Id
	 * @param childCategoryName 中カテゴリ-Id
	 * @return 省カテゴリーリスト
	 */
	@RequestMapping("/findByGrandCategoryName")
	@ResponseBody
	public List<Category> getGrandCategoryList(Integer parent, String childCategoryName) {
		List<Category> GrandCategoryList = itemListService.getGrandCategoryList(parent, childCategoryName);
		return GrandCategoryList;
	}

//	@RequestMapping("/serachItems")
//	public String searchItem(SearchForm searchForm, Model model) {
//		System.out.println(searchForm);
//		Integer offset = 0;
//		List<Item> itemList = itemListService.searchItemList(searchForm, offset);
//		Integer nowPage = 0;
//		model.addAttribute("itemList", itemList);
//		model.addAttribute("nowPage", nowPage);
//		return "list";
//	}

	@RequestMapping("/findBySession")
	@ResponseBody
	public SearchForm findBySession() {
		SearchForm searchForm = (SearchForm) session.getAttribute("SearchForm");
		return searchForm;
	}

}
