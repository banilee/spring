package chap07.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImple implements UserService {

	@Autowired
	UserDao dao;
	
	@Override
	@Transactional
	public boolean insert(UserVo vo, HttpServletRequest req) {
		boolean r = false;
		
		int cnt = dao.insert(vo);
		// school데이터 등록
		// UserVo 객체에는 Userno가 저자아된 상태
//		String[] school = req.getParameterValues("school");
//		String[] year = req.getParameterValues("year");
//		SchoolVo svo = new SchoolVo();
//		svo.setUserno(vo.getUserno());
//		
//		for (int i=0; i<school.length; i++) {
//			svo.setSchool(school[i]);
//			svo.setYear(year[i]);
//			cnt += dao.insertSchool(svo);
//			
//		}
		SchoolVo svo = new SchoolVo();
		svo.setUserno(vo.getUserno());
		
		for (int i=0; i<vo.getSchool().length; i++) {
			svo.setSchool(vo.getSchool()[i]);
			svo.setYear(vo.getYear()[i]);
			cnt += dao.insertSchool(svo);
		}
		
		if (cnt == vo.getSchool().length+1){
			r = true;
		}
		
		return r;
	}

	@Override
	public int idcheck(String id) {
		return dao.idcheck(id);
	}

	@Override
	public boolean login(UserVo vo, HttpSession sess) {
		UserVo uv = dao.login(vo);
		if (uv != null) { // 로그인성공
			sess.setAttribute("loginInfo", uv);
			return true;
		}
		return false;
	}

}
