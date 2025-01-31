package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor // final이 붙은 변수의 생성자를 만들어준다.
public class BasicItemController {

    private final ItemRepository itemRepository;

//    @Autowired  //생성자가 이거하나면 생략할 수 있다. @RequiredArgsConstructor로 필요없어서 주석처리
//    public BasicItemController(ItemRepository itemRepository) {
//        this.itemRepository = itemRepository;
//    }

    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";

    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "basic/item";

    }

    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";

    }

//    @PostMapping("/add")
//    public String addItemV1(@RequestParam String itemName,
//                        @RequestParam int price,
//                       @RequestParam Integer quantity,
//                       Model model){
//        Item item = new Item();
//
//        item.setItemName(itemName);
//        item.setPrice(price);
//        item.setQuantity(quantity);
//
//        itemRepository.save(item);
//
//        model.addAttribute("item", item);
//
//        return "basic/item";
//
//    }

//    @PostMapping("/add")
//    public String addItemV2(@ModelAttribute("item") Item item){
//
//        itemRepository.save(item);
//
//        /* @ModelAttribute("item") 어노테이션에 ()에 넣어놓은 이름으로 addAttribute를 자동으로 실행해줌*/
////        model.addAttribute("item", item);
//
//        return "basic/item";
//
//    }

//    @PostMapping("/add")
//    public String addItemV3(@ModelAttribute Item item){
//
//        // @ModelAttribute에 ()로 이름을 지정해주지않으면
//        // 클래스명의 첫글자를 소문자로 바꿔서 모델에 담긴다. Item -> item
//        itemRepository.save(item);
//
//        return "basic/item";
//    }
//
//    /**
//     * @ModelAttribute 자체 생략 가능
//     * model.addAttribute(item) 자동 추가
//     */
//    @PostMapping("/add")
//    public String addItemV4(Item item) {
//        itemRepository.save(item);
//        return "basic/item";
//    }

    /**
     * PRG - Post/Redirect/Get
     */
    @PostMapping("/add")
    public String addItemV5(Item item) {
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {

        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, Model model, @ModelAttribute Item item) {

        itemRepository.update(itemId, item);

        return "redirect:/basic/items/{itemId}";
    }



    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 25000, 28));
    }
}
