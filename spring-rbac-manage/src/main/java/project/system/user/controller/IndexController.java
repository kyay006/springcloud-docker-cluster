package project.system.user.controller;

import framework.config.RuoYiConfig;
import framework.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import project.system.menu.domain.Menu;
import project.system.menu.service.IMenuService;
import project.system.user.domain.User;

import java.util.List;

/**
 * 首页 业务处理
 * 
 * @author wave
 */
@Controller
public class IndexController extends BaseController
{
    @Autowired
    private IMenuService menuService;

    @Autowired
    private RuoYiConfig ruoYiConfig;

    // 系统首页
    @GetMapping("/index")
    public String index(ModelMap mmap)
    {
        // 取身份信息
        User user = getUser();
        // 根据用户id取出菜单
        List<Menu> menus = menuService.selectMenusByUserId(user.getUserId());
        List<Menu> children = null;
        for (int i = 0,iLen = menus.size(); i < iLen; i++) {
            if(menus.get(i).getMenuName().equals("系统工具")){
                menus.remove(i);
                i--;
                iLen--;
            }
            children = menus.get(i).getChildren();
            for (int j = 0,jLen = children.size(); j < jLen; j++) {
                if(children.get(j).getUrl().equals("/system/config") || children.get(j).getUrl().equals("/system/dict")){
                    children.remove(j);
                    j--;
                    jLen--;
                }
            }
        }
        
        
        mmap.put("menus", menus);
        mmap.put("user", user);
        mmap.put("copyrightYear", ruoYiConfig.getCopyrightYear());
        return "index";
    }

    // 系统介绍
    @GetMapping("/system/main")
    public String main(ModelMap mmap)
    {
        mmap.put("version", ruoYiConfig.getVersion());
        return "main";
    }

}
