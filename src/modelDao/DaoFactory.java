package modelDao;

import DB.DB;
import modelDaoImpl.AlunoDaoJDBC;
import modelDaoImpl.CursoDaoJDBC;
import modelDaoImpl.EmprestimoDaoJDBC;
import modelDaoImpl.FaculdadeDaoJDBC;
import modelDaoImpl.FuncionarioDaoJDBC;
import modelDaoImpl.LivroDaoJDBC;
import modelDaoImpl.OrientadorDaoJDBC;
import modelDaoImpl.PessoaDaoJDBC;
import modelDaoImpl.ReservaDaoJDBC;
import modelDaoImpl.TrabalhoDaoJDBC;

public class DaoFactory {
    public static AlunoDao createAlunoDao(){
        return new AlunoDaoJDBC(DB.getConnection());
    }
    public static EmprestimoDao createEmprestimoDao(){
        return new EmprestimoDaoJDBC(DB.getConnection());
    }
    public static FuncionarioDao createFuncionarioDao(){
        return new FuncionarioDaoJDBC(DB.getConnection());
    }
    public static LivroDao createLivroDao(){
        return new LivroDaoJDBC(DB.getConnection());
    }
    public static OrientadorDao createOrientadorDao(){
        return new OrientadorDaoJDBC(DB.getConnection());
    }
    public static ReservaDao creatReservaDao(){
        return new ReservaDaoJDBC(DB.getConnection());
    }
    public static TrabalhoDao createTrabalhoDao(){
        return new TrabalhoDaoJDBC(DB.getConnection());
    }
    public static PessoaDao createPessoaDao(){
        return new PessoaDaoJDBC(DB.getConnection());
    }
    public static CursoDao createCursoDao(){
        return new CursoDaoJDBC(DB.getConnection());
    }
    public static FaculdadeDao createFaculdadeDao(){
        return new FaculdadeDaoJDBC(DB.getConnection());
    }
}
