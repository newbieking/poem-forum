package com.example.controller;

import com.example.pojo.Poem;
import com.example.pojo.PoemDoc;
import com.example.service.LoginService;
import com.example.service.PoemService;
import com.example.service.RecentPoemService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class SearchController {

    @NonNull PoemService poemService;

    @NonNull RecentPoemService recentPoemService;

    @GetMapping("search")
    public String search(@RequestParam("search") String search,
                         @RequestParam("token") String token,
                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                         @RequestParam(value = "size", defaultValue = "10") Integer size,
                         Model model
    ) throws IOException {
        String[] parsedToken = LoginService.parseToken(token);
        String userId = parsedToken[0];
        Page<PoemDoc> poemDocs = poemService.searchPoemDoc(search, PageRequest.of(page, size));
        List<Poem> topPoems = poemService.topPoems();
        ArrayList<String> recentSearches = new ArrayList<>();
        recentSearches.add(search);
        recentPoemService.updateRecentSearch(userId, recentSearches);
        model.addAttribute("token", token);
        model.addAttribute("poems", poemDocs.getContent());
        model.addAttribute("topPoems", topPoems);
        model.addAttribute("search", search);
        return "home";
    }

}
