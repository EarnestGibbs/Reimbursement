package TestDao;

import com.gibbs.data.ReimbursementDAO;
import com.gibbs.data.ReimbursementPostgres;
import com.gibbs.data.ReimbursementStatusDAO;
import com.gibbs.data.ReimbursementStatusPostgres;
import com.gibbs.data.ReimbursementTypeDAO;
import com.gibbs.data.ReimbursementTypePostgres;
import com.gibbs.data.UserDAO;
import com.gibbs.data.UserPostgres;
import com.gibbs.models.Reimbursement;
import com.gibbs.models.ReimbursementStatus;
import com.gibbs.models.ReimbursementType;
import com.gibbs.utils.ConnectionUtil;

public class DaoTesting {

	public static void main(String[] args) {
//		ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
//		
//		cu.getConnection();
		
		UserDAO u = new UserPostgres();
		ReimbursementDAO r = new ReimbursementPostgres();
		ReimbursementTypeDAO t = new ReimbursementTypePostgres();
		ReimbursementStatusDAO s = new ReimbursementStatusPostgres();
		Reimbursement re = new Reimbursement();
		ReimbursementType rt = new ReimbursementType();
		ReimbursementStatus rs = new ReimbursementStatus();
		
		
//		System.out.println(t.getTypeById(1));
//		System.out.println(s.getTypeById(1));
		
//		re.setAuthorUser(u.getUserById(4));
//		re.setStatus();
//		System.out.println(re.getAuthorUser());
//		System.out.println(u.getUsers());
//		System.out.println(u.getUserById(4));
//		System.out.println(r.getReimbursementById(2));
//		System.out.println(r.getReimbursements());
//		System.out.println(r.getReimbursementByUserId(4));
		
//		System.out.println(r.getReimbursementsByType(rt));
//		System.out.println(r.getReimbursementsByStatus(rs));
		//System.out.println(r.get);
		System.out.println(r.getReimbursements());
		//String sql = "{call validateUser(?,?)}";
		
//		System.out.println(u.getUserByUsernameAndPassword("finly", "fish"));
		
		//System.out.println(r.createReimbursement(re));
		
		
	}

}
