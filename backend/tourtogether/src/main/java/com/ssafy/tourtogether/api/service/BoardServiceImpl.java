package com.ssafy.tourtogether.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ssafy.tourtogether.api.request.BoardAddParticipantPostReq;
import com.ssafy.tourtogether.api.request.BoardCategoryPostReq;
import com.ssafy.tourtogether.api.request.BoardClickBoardLikePatchReq;
import com.ssafy.tourtogether.api.request.BoardCreatePostReq;
import com.ssafy.tourtogether.api.request.BoardDeleteDeleteReq;
import com.ssafy.tourtogether.api.request.BoardFinishPatchReq;
import com.ssafy.tourtogether.api.request.BoardSearchByBoardIdGetReq;
import com.ssafy.tourtogether.api.request.BoardSearchByUserIdGetReq;
import com.ssafy.tourtogether.db.entity.Board;
import com.ssafy.tourtogether.db.entity.BoardLikes;
import com.ssafy.tourtogether.db.entity.BoardParticipant;
import com.ssafy.tourtogether.db.entity.Category;
import com.ssafy.tourtogether.db.repository.BoardLikesRepository;
import com.ssafy.tourtogether.db.repository.BoardLikesRepositorySupport;
import com.ssafy.tourtogether.db.repository.BoardParticipantRepository;
import com.ssafy.tourtogether.db.repository.BoardParticipantRepositorySupport;
import com.ssafy.tourtogether.db.repository.BoardRepository;
import com.ssafy.tourtogether.db.repository.BoardRepositorySupport;
import com.ssafy.tourtogether.db.repository.CategoryRepository;
import com.ssafy.tourtogether.db.repository.CategoryRepositorySupport;

/**
 * 보드 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("boardService")
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardRepository boardRepository;
	@Autowired
	BoardParticipantRepository boardParticipantRepository;
	@Autowired
	BoardLikesRepository boardLikesRepository;
	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	BoardRepositorySupport boardRepositorySupport;
	@Autowired
	BoardParticipantRepositorySupport boardParticipantRepositorySupport;
	@Autowired
	BoardLikesRepositorySupport boardLikesRepositorySupport;
	@Autowired
	CategoryRepositorySupport categoryRepositorySupport;

	@Override
	public void createBoard(BoardCreatePostReq boardCreateInfo) {
		Board board = new Board();
		board.setBoardName(boardCreateInfo.getBoardName());
		board.setBoardIsActive(false); // 보드가 처음에 생성됐을때는 무조건 완료되지 않은 일정임으로 false로 설
		boardRepository.save(board);
		// 참가자 추가
		BoardParticipant boardParticipant = new BoardParticipant();
		boardParticipant.setBoardParticipantBoardId(board.getBoardId());
		boardParticipant.setBoardParticipantUserId(boardCreateInfo.getUserId());
		boardParticipantRepository.save(boardParticipant);
	}

	@Override
	public void finishBoard(BoardFinishPatchReq boardFinishInfo) {
		boardRepositorySupport.updateFinish(boardFinishInfo);
	}

	@Override
	public void deleteBoard(BoardDeleteDeleteReq boardDeleteInfo) {
		// board 테이블에서 삭제
		// cascade로 boardId로 묶여있는거 전부 삭제됨
		boardRepositorySupport.delete(boardDeleteInfo);
	}

	@Override
	public Optional<Board> searchByBoardId(BoardSearchByBoardIdGetReq boardSearchByBoardIdInfo) {
		Optional<Board> board = boardRepository.findById(boardSearchByBoardIdInfo.getBoardId());
		return board;
	}

	@Override
	public List<Board> searchByUserId(BoardSearchByUserIdGetReq boardSearchByUserIdInfo) {
		List<Board> myBoards = boardRepositorySupport.findByUserId(boardSearchByUserIdInfo);
		return myBoards;
	}

	@Override
	public List<Board> searchLikeBoardByUserId(BoardSearchByUserIdGetReq boardSearchByUserIdInfo) {
		List<Board> myLikeBoards = boardRepositorySupport.findLikeBoardByUserId(boardSearchByUserIdInfo);
		return myLikeBoards;
	}

	@Override
	public List<Board> searchAll() {
		// 좋아요 개수를 기준으로 내림차순 정렬
		List<Board> boards = boardRepository.findAll(Sort.by(Sort.Direction.DESC, "boardLikesCount"));
		return boards;
	}

	@Override
	public void addParticipant(BoardAddParticipantPostReq boardAddParticipantInfo) {
		BoardParticipant boardParticipant = new BoardParticipant();
		boardParticipant.setBoardParticipantBoardId(boardAddParticipantInfo.getBoardId());
		boardParticipant.setBoardParticipantUserId(boardAddParticipantInfo.getUserId());
		boardParticipantRepository.save(boardParticipant);

	}

	@Override
	public void clickBoardLike(BoardClickBoardLikePatchReq boardclickBoardLikeInfo) {
		// 좋아요 누른 보드 아이디 저장
		BoardLikes boardLikes = new BoardLikes();
		boardLikes.setBoardLikesBoardId(boardclickBoardLikeInfo.getBoardId());
		boardLikes.setBoardLikesUserId(boardclickBoardLikeInfo.getUserId());
		boardLikesRepository.save(boardLikes);
		// 좋아요 누른 보드 아이디의 좋아요 개수 +1
		boardRepositorySupport.increaseLike(boardclickBoardLikeInfo.getBoardId());
	}

	@Override
	public void cancelBoardLike(BoardClickBoardLikePatchReq boardclickBoardLikeInfo) {
		// 좋아요 누른 보드 아이디 삭제
		boardLikesRepositorySupport.deleteByBoardId(boardclickBoardLikeInfo);
		// 좋아요 누른 보드 아이디의 좋아요 개수 -1
		boardRepositorySupport.decreaseLike(boardclickBoardLikeInfo.getBoardId());
	}

	@Override
	public void category(BoardCategoryPostReq boardCategoryInfo) {
		Category category = new Category();
		category.setCategoryBoardId(boardCategoryInfo.getBoardId());
		category.setCategoryWithWhom(boardCategoryInfo.getCategoryWithWhom());
		category.setCategorySeason(boardCategoryInfo.getCategorySeason());
		category.setCategoryArea(boardCategoryInfo.getCategoryArea());
		category.setCategoryTheme(boardCategoryInfo.getCategorySeason());
		categoryRepository.save(category);
	}

}