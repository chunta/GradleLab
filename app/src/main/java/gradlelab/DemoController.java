package gradlelab;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

import java.util.ArrayList;

@RestController
public class DemoController {

    @GetMapping("/records")
    public ModelAndView helloWorld() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("records");

        ArrayList<Record> records = new ArrayList<>();
        records.add(new Record("John Doe", 25, "john.doe@example.com"));
        records.add(new Record("Jane Smith", 30, "jane.smith@example.com"));
        records.add(new Record("Bob Johnson", 40, "bob.johnson@example.com"));

        modelAndView.addObject("records", records);

        return modelAndView;
    }

    @GetMapping("/demo")
    public ModelAndView helloWorld(@RequestParam(name="name", required=false, defaultValue="World") String name,
                                   Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("demo");
        modelAndView.addObject("name", name);
        return modelAndView;
    }

    @GetMapping("/demo_introduction")
    public ModelAndView showIntroduction() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("demo_introduction");
        modelAndView.addObject("name", "rex");
        modelAndView.addObject("showMessage", false);
        modelAndView.addObject("numbers", new int[] { 1, 2, 3, 4, 5 });
        return modelAndView;
    }


}