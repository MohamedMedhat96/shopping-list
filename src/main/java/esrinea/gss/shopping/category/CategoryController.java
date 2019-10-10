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

@RestController
public class CategoryController {
	//@Autowired
	//private final CategoryRepository categoryRepo;
	@Autowired
	private  CategoryService categoryService;
	
	public CategoryController() {
		
	}
	@GetMapping("/category")
	CategoryDTO getAllCategories(){
		return categoryService.getAllCategories();
	}
	@PostMapping("/category")
	@ResponseStatus(HttpStatus.CREATED)
	CategoryDTO addCategory(@RequestBody CategoryModel category)
	{
		return categoryService.addCategory(category);
	}
	@PutMapping("/category/{id}")
	CategoryDTO editCategory(@PathVariable int id,@RequestBody Map<String, String> json)
	{
		
		return categoryService.editCategory(id,json.get("name"),json.get("description"));
		
	}
	
	@DeleteMapping("/category/{id}")
	CategoryDTO deleteCategory(@PathVariable int id) {
		return categoryService.deleteCategory(id); 
		
	}
	
	@GetMapping("/category/{id}")
	CategoryDTO getCategory(@PathVariable int id) {
		return categoryService.getCategory(id);
	}
	
}
