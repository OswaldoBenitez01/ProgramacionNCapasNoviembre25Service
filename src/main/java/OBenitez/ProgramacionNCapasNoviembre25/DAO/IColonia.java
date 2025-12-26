
package OBenitez.ProgramacionNCapasNoviembre25.DAO;

import OBenitez.ProgramacionNCapasNoviembre25.JPA.Result;

public interface IColonia {
    public Result GetColoniasByMunicipio(int idMunicipio);
}
