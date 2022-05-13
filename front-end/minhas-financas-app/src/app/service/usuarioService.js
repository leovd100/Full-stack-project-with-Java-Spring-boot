import ApiService from "../apiService";

class UsuarioService extends ApiService{
    constructor(){
        super('/api/usuarios')
    }

    autenticar(credenciais){
        return this.post('/autenticar',credenciais)
    }

    opterSaldoPorUsuario(id){
        return this.get(`/${id}/saldo`)
    }
}

export default UsuarioService