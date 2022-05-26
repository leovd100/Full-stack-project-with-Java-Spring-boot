import React from "react";
import AuthService from "../app/service/authService";
import LocalStorageService from "../app/service/localStorageService";
import { USUARIO_LOGADO } from "../app/service/authService";
export const AuthContext = React.createContext()
export const AuthCosumer = AuthContext.Consumer;
const AuthProvider = AuthContext.Provider;

class ProvedorAutenticacao extends React.Component{
    state = {
        usuarioAutenticado: null,
        isAutenticado: false
    }

    iniciarSessao = (usuario) => {
        
        this.setState({isAutenticado: true, usuarioAutenticado: usuario})
        LocalStorageService.adicionarItem("STADO_PAGE", true)
        AuthService.logar(usuario)
        
    }
    encerrarSessao = () => {
        AuthService.removerUsuarioAutenticado()
        LocalStorageService.adicionarItem("STADO_PAGE", false)
        this.setState({isAutenticado: false, usuarioAutenticado: null})
    }
    

    render(){
        const contexto = {
            usuarioAutenticado: LocalStorageService.obterItem(USUARIO_LOGADO),
            isAutenticado: LocalStorageService.obterItem("STADO_PAGE") === true ? true : false,
            iniciarSessao: this.iniciarSessao,
            encerrarSessao: this.encerrarSessao
        }
        
        return(
          
            
            <AuthProvider value={contexto}>
                {this.props.children}
            </AuthProvider>
        )
    }
}


export default ProvedorAutenticacao