package com.ssafy.tourtogether.memo.controller;

import com.ssafy.tourtogether.memo.model.MemoRoom;
import com.ssafy.tourtogether.memo.repository.MemoRoomRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/memo")
public class MemoRoomController {

    private final MemoRoomRepository memoRoomRepository;

    @GetMapping("/room")
    public String rooms(Model model) {
        return "/memo/room";
    }

    @GetMapping("/rooms")
    @ResponseBody
    public List<MemoRoom> room() {
        return memoRoomRepository.findAllRoom();
    }

    @PostMapping("/room")
    @ResponseBody
    public MemoRoom createRoom(@RequestParam String name) {
    	System.out.println("name: "+name);
        return memoRoomRepository.createMemoRoom(name);
    }

    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        return "/memo/roomdetail";
    }

    @GetMapping("/room/{roomId}")
    @ResponseBody
    public MemoRoom roomInfo(@PathVariable String roomId) {
        return memoRoomRepository.findRoomById(roomId);
    }
}
