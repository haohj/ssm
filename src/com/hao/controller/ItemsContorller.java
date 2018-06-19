package com.hao.controller;

import com.hao.controller.validation.ValidGroup1;
import com.hao.exception.CustomException;
import com.hao.po.ItemsCustom;
import com.hao.po.ItemsQueryVo;
import com.hao.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Controller
public class ItemsContorller {
    @Autowired
    private ItemsService itemsService;

    @RequestMapping(value = "/queryItems", method = {RequestMethod.GET, RequestMethod.POST})
    public String queryItems(Model model, ItemsQueryVo itemsQueryVo) throws Exception {
        List<ItemsCustom> itemsCustomList = itemsService.findItemsList(itemsQueryVo);
        model.addAttribute("itemsList", itemsCustomList);
        return "WEB-INF/jsp/items/itemsList.jsp";
    }

    @RequestMapping(value = "/findItems/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    ItemsCustom findItems(@PathVariable("id") Integer id) throws Exception {
        ItemsCustom itemsCustom = itemsService.findItemsById(id);
        return itemsCustom;
    }

    @RequestMapping(value = "/goUpdate", method = {RequestMethod.GET})
    public String goUpdate(Model model, Integer id) throws Exception {
        ItemsCustom itemsCustom = itemsService.findItemsById(id);
        if (itemsCustom == null) {
            throw new CustomException("修改的商品信息不存在");
        }
        model.addAttribute("items", itemsCustom);
        return "WEB-INF/jsp/items/editItems.jsp";
    }

    @RequestMapping(value = "/batchDeleteItems", method = {RequestMethod.POST})
    public String batchDeleteItems(Integer[] items_id) throws Exception {
        for (int i = 0; i < items_id.length; i++) {
            System.out.println(items_id[i]);
        }
        return "WEB-INF/jsp/success.jsp";
    }

    @RequestMapping(value = "/doUpdate", method = {RequestMethod.POST})
    public String doUpdate(Model model, Integer id, @Validated(value = {ValidGroup1.class}) ItemsCustom itemsCustom, BindingResult bindingResult, MultipartFile items_pic) throws Exception {
        // 获取校验错误信息
        if (bindingResult.hasErrors()) {
            // 输出错误信息
            List<ObjectError> allErrors = bindingResult.getAllErrors();

            for (ObjectError objectError : allErrors) {
                // 输出错误信息
                System.out.println(objectError.getDefaultMessage());

            }
            // 将错误信息传到页面
            model.addAttribute("allErrors", allErrors);


            //可以直接使用model将提交pojo回显到页面
            model.addAttribute("items", itemsCustom);

            // 出错重新到商品修改页面
            return "WEB-INF/jsp/items/editItems.jsp";
        }

        //原始名称
        String originalFilename = items_pic.getOriginalFilename();
        //上传图片
        if (items_pic != null && originalFilename != null && originalFilename.length() > 0) {

            //存储图片的物理路径
            String pic_path = "E:\\develop\\upload\\temp\\";


            //新的图片名称
            String newFileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
            //新图片
            File newFile = new File(pic_path + newFileName);

            //将内存中的数据写入磁盘
            items_pic.transferTo(newFile);

            //将新图片名称写到itemsCustom中
            itemsCustom.setPic(newFileName);

        }


        // 调用service更新商品信息，页面需要将商品信息传到此方法
        itemsService.updateItems(id, itemsCustom);


        return "redirect:queryItems.action";
    }
}
