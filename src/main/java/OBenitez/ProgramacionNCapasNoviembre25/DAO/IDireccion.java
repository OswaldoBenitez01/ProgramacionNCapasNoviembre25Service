package OBenitez.ProgramacionNCapasNoviembre25.DAO;

import OBenitez.ProgramacionNCapasNoviembre25.JPA.Direccion;
import OBenitez.ProgramacionNCapasNoviembre25.JPA.Result;

public interface IDireccion {
    public Result AddAddress(Direccion direccion);
    public Result UpdateAddressById(Direccion direccion);
    public Result DeleteAddressById(int IdDireccion);
}
