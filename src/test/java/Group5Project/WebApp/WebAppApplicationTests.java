package Group5Project.WebApp;


import Group5Project.WebApp.Data.Cart;
import Group5Project.WebApp.Data.CartCollection;
import Group5Project.WebApp.controller.HelperMethods;
import Group5Project.WebApp.controller.IndexController;
import Group5Project.WebApp.model.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class WebAppApplicationTests {


	private HelperMethods helperMethods = new HelperMethods();

	@Test
	void TryParseDoublereturnsDouble() {

		assertThat(helperMethods.tryParseDouble("55.2", 0)).isEqualTo(55.2);
	}

	@Test
	void TryParseDoublereturnsDefaultWhenNotDouble() {

		assertThat(helperMethods.tryParseDouble("not a double value", 0)).isEqualTo(0);
	}

	@Test
	void TryParseDoublereturnsint() {

		assertThat(helperMethods.tryParseInt("55")).isEqualTo(55);
	}

	@Test
	void TryParseIntreturnsDefaultWhenNotInt() {

		assertThat(helperMethods.tryParseInt("not an int value")).isEqualTo(-1);
	}

	@Test
	void getDayMonthYearReturnsPropperFormat() {
		String date = helperMethods.getDayMonthYear();

		String year = date.substring(date.length() - 4);
		String day = date.substring(0, Math.min(date.length(), 2));

		int yearInt = helperMethods.tryParseInt(year);
		int dayInt = helperMethods.tryParseInt(day);

		assertThat(yearInt).isNotEqualTo(-1);
		assertThat(dayInt).isNotEqualTo(-1);
	}

	@Test
	void CartExistsReturnsTrueIfCartExists() {
		Cart cart = new Cart("testUserName");
		CartCollection.AllCarts.add(cart);

		boolean actual = helperMethods.CartExists("testUserName");

		assertThat(actual).isEqualTo(true);

	}

	@Test
	void CartExistsReturnsFalseIfCartDoseNotExists() {
		Cart cart = new Cart("testUserName");
		CartCollection.AllCarts.add(cart);

		boolean actual = helperMethods.CartExists("does not exist");

		assertThat(actual).isEqualTo(false);

	}

	@Test
	void GetCartQuantityReturnsCorrectAmount() {

		Cart cart = new Cart("testUserName");
		CartCollection.AllCarts.add(cart);

		Item item = new Item(0001, "test item", 1, 1, "des", "cat");

		cart.addItem(item);

		int actual = helperMethods.GetCartQuantity(cart);

		assertThat(actual).isEqualTo(1);

	}

	@Test
	void GetCartByUserNameReturnsCorrectCart()
	{
		Cart cart = new Cart("testUserName");
		CartCollection.AllCarts.add(cart);

		Item item = new Item(0001, "test item", 1, 1, "des", "cat");
		cart.addItem(item);

		Cart foundCart = helperMethods.GetCartByUserName("testUserName");

		assertThat(foundCart.ItemsInCart.size()).isEqualTo(1);
	}


}
