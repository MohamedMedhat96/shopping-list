package esrinea.gss.shopping.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepo;
	
	public CategoryService() {
	//	this.categoryRepo = new CategoryRepository();
		//this.categoryRepo = new CategoryRepository();
		
	}
	
	
	
	public CategoryDTO getAllCategories(){
		CategoryDTO x = new CategoryDTO();
		x.setHttpMessage("DONE");
		x.setHttpStatusCode(200);
		x.setData(categoryRepo.getAllCategories());
		return x;
	}
	
	public CategoryDTO addCategory(CategoryModel category) {
		category = categoryRepo.addCategory(category);
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setHttpMessage("The Category Have been Added!");
		categoryDTO.setHttpStatusCode(201);
		categoryDTO.setData(category.getId());
		return categoryDTO;
	}

	public CategoryDTO editCategory(int id, String name, String description) {
		// TODO Auto-generated method stub
		categoryRepo.editCategory(id, name, description);
		CategoryDTO data = new CategoryDTO();
		data.setHttpMessage("Done!");
		data.setHttpStatusCode(200);
		return data;
	}

	public CategoryDTO deleteCategory(int id) {
		categoryRepo.deleteCategory(id);
		CategoryDTO data = new CategoryDTO();
		data.setHttpMessage("Done!");
		data.setHttpStatusCode(200);
		return data;
	}

	public CategoryDTO getCategory(int id) {
		
		CategoryModel currentCategory = categoryRepo.getACategory(id);
		CategoryDTO output = new CategoryDTO();
		output.setData(currentCategory);
		output.setHttpMessage("Done!");
		output.setHttpStatusCode(200);
		return output;
	}
}
	

