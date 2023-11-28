package com.getNumber;


import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RestController
@RequestMapping(value = "/quhao")
@Tag(name = "mainController", description = "取号接口")
public class mainController {
    @Autowired
    private DataSource dataSource;

    @Operation(summary = "取号", method = "POST")
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getNumberController(@Parameter(hidden = true) HttpServletRequest httpServletRequest, @Schema(type ="json",example = "{\"type\":[\"default\"]}") @RequestBody JSONObject req, @Parameter(hidden = true) Response res){
        if (req == null) {
            res.setMessage("param error");
            return null;
        }else {
            return getResult(req);
        }
    }

    private JSONObject getResult(JSONObject json){
        if(json == null || json.get("type") == null) return null;
        JSONArray inp = JSONArray.from(json.get("type"));
        JSONObject res = new JSONObject();
        for(int i=0; i<inp.size(); i++){
            res.put(inp.getString(i),queryValue(inp.getString(i)));
        }
        return res;
    }

    private String queryValue(String key) {
        try {
            String sql = "SELECT getsequence(?)";
            Connection con = dataSource.getConnection();
            PreparedStatement pres = con.prepareStatement(sql);
            pres.setString(1,key);
            ResultSet rs = pres.executeQuery();
            String rsult = "";
            if (rs.next()) {
                rsult = rs.getString(1);
            }
            con.close();
            pres.close();
            rs.close();
            return rsult;
        }catch (SQLException e){
            return "";
        }

    }
}
