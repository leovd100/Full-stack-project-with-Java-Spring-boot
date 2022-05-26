import LocalStorageService from "./localStorageService";
export const USUARIO_LOGADO = '_usuarioLogado'
export default class AuthService{
    
    static isUsuarioAutenticado(){
        const usuario = LocalStorageService.obterItem(USUARIO_LOGADO)
        return usuario && usuario.id
    }

    static removerUsuarioAutenticado(){
        LocalStorageService.removerItem(USUARIO_LOGADO)
        //LocalStorageService.removerItem("STADO_PAGE")
    }

    static logar(usuario){
     
        LocalStorageService.adicionarItem(USUARIO_LOGADO, usuario)
    }

    static obterUsuarioAutenticado(){
        return LocalStorageService.obterItem(USUARIO_LOGADO)
    }
}