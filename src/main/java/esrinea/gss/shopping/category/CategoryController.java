package esrinea.gss.shopping.category;

import java.util.List;
import java.util.Locale.Category;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * A controller interface that handles REST requests related to categories
 */
@RestController
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	public CategoryController() {

	}

	/**
	 * Submit an empty GET request to /category in order to receive all categories
	 * available
	 * 
	 * @return JSON with all the categories, a success message and a success code
	 *         200
	 */
	@GetMapping("/category")
	@ResponseStatus(HttpStatus.ACCEPTED)
	CategoryDTO getAllCategories() {
		return categoryService.getAllCategories();
	}

	/**
	 * Submit a POST request to /category with fields "name" and "description" to
	 * create a new category with the given name and category
	 * 
	 * @param category
	 * @return JSON with the newly created category's ID and a success code and
	 *         message
	 */
	@PostMapping("/category")
	@ResponseStatus(HttpStatus.CREATED)
	CategoryDTO addCategory(@RequestBody CategoryModel category) {
		return categoryService.addCategory(category);
	}

	/**
	 * Submit a PUT request with fields "name" and "description" to /category/{id}
	 * with {id} being replaced with the category ID you want to edit and the "name"
	 * and "description" you want to be edited
	 * 
	 * @param id   category ID you want to edit
	 * @param json with name and description fields
	 * @return JSON with success message and code
	 */
	@PutMapping("/category/{id}")
	CategoryDTO editCategory(@PathVariable int id, @RequestBody Map<String, String> json) {

		return categoryService.editCategory(id, json.get("name"), json.get("description"));

	}

	/**
	 * // Submit an empty DELETE request to /category/{id} with {id} being replaced
	 * // with the category's ID you want to delete
	 * 
	 * @param id category's ID you want to delete
	 * @return JSON with succes message and code
	 */
	@DeleteMapping("/category/{id}")
	CategoryDTO deleteCategory(@PathVariable int id) {
		return categoryService.deleteCategory(id);

	}

	/**
	 * // Submit an empty GET request to /category/{id} with {id} being replaced
	 * with // the category's ID that you want to get
	 * @param id category's ID you want to get
	 * @return JSON with succes message and code
	 */
	@GetMapping("/category/{id}")
	CategoryDTO getCategory(@PathVariable int id) {
		return categoryService.getCategory(id);
	}

}
