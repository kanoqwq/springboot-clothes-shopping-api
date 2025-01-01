package com.kano.springbootmongoclothesapi.controller;

import com.kano.springbootmongoclothesapi.config.RolesConfig;
import com.kano.springbootmongoclothesapi.utils.JwtToken;
import com.kano.springbootmongoclothesapi.utils.RequestJWT;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 动态路由
 */
@RestController
@RequestMapping("/api/routeList")
public class DynamicRoute {

    @GetMapping
    public ResponseEntity<Object> getRouteList(){
        HashMap<String,String> map =  RequestJWT.getUserInfo();
        if (map == null) return new ResponseEntity<>(HttpStatusCode.valueOf(401));

        String token = map.get("token");
        String role = JwtToken.getRole(token);
        return switch (role) {
            case RolesConfig.SYSTEM_ADMIN -> ResponseEntity.ok(adminRoute());
            case RolesConfig.PRODUCER -> ResponseEntity.ok(producerRoute());
            case RolesConfig.SELLER -> ResponseEntity.ok(sellerRoute());
            case RolesConfig.WAREHOUSE_MANAGER -> ResponseEntity.ok(warehouseRoute());
            default -> ResponseEntity.ok(new ArrayList<>());
        };
    }

    private List<Map<String, Object>> adminRoute(){
        List<Map<String, Object>> routes = new ArrayList<>();
        routes.add(createRoute("/datascreen", "datascreen", createRoute("/datascreen")));
        routes.add(createRoute("/userManager", "userManager", createRoute("/userManager")));
        routes.add(createRoute("/userInfo", "userInfo", createRoute("/userInfo")));
        routes.add(createRoute("/producer", "producer", createRoute("/producer")));
        routes.add(createRoute("/purchases", "purchases", createRoute("/purchases")));
        routes.add(createRoute("/warehouse", "warehouse", createRoute("/warehouse")));
        routes.add(createRoute("/inventory", "inventory", createRoute("/inventory")));
        routes.add(createRoute("/sale", "sale", createRoute("/sale")));
        return routes;
    }

    private List<Map<String, Object>> producerRoute(){
        List<Map<String, Object>> routes = new ArrayList<>();
        routes.add(createRoute("/datascreen", "datascreen", createRoute("/datascreen")));
        routes.add(createRoute("/userInfo", "userInfo", createRoute("/userInfo")));
        routes.add(createRoute("/producer", "producer", createRoute("/producer")));
        return routes;
    }
    private List<Map<String, Object>> sellerRoute(){
        List<Map<String, Object>> routes = new ArrayList<>();
        routes.add(createRoute("/datascreen", "datascreen", createRoute("/datascreen")));
        routes.add(createRoute("/userInfo", "userInfo", createRoute("/userInfo")));
        routes.add(createRoute("/purchases", "purchases", createRoute("/purchases")));
        routes.add(createRoute("/inventory", "inventory", createRoute("/inventory")));
        routes.add(createRoute("/sale", "sale", createRoute("/sale")));
        routes.add(createRoute("/qrCode", "qrCode", createRoute("/qrCode")));
        routes.add(createRoute("/saleCode", "saleCode", createRoute("/saleCode")));
        return routes;
    }

    private List<Map<String, Object>> warehouseRoute(){
        List<Map<String, Object>> routes = new ArrayList<>();
        routes.add(createRoute("/datascreen", "datascreen", createRoute("/datascreen")));
        routes.add(createRoute("/userInfo", "userInfo", createRoute("/userInfo")));
        routes.add(createRoute("/inventory", "inventory", createRoute("/inventory")));
        routes.add(createRoute("/warehouse", "warehouse", createRoute("/warehouse")));
        routes.add(createRoute("/qrCode", "qrCode", createRoute("/qrCode")));
        return routes;
    }


    // 创建单个路由的工具方法
    private Map<String, Object> createRoute(String path, String name,Map<String, Object> children) {
        Map<String, Object> route = new HashMap<>();
        route.put("path", path);
        route.put("name", name);
        route.put("children", children);
        return route;
    }
    private Map<String, Object> createRoute(String path) {
        Map<String, Object> route = new HashMap<>();
        route.put("path", path);
        return route;
    }

}