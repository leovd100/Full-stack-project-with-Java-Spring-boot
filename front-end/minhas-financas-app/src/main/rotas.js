import React from "react";
import Login from "../views/login";
import CadastroUsuario from "../views/cadastrUsuario";
import { Route, Routes  , HashRouter} from 'react-router-dom'

function Rotas(){
    return(
        <HashRouter>
            <Routes>
                <Route path="/login" element={<Login/>}></Route>
                <Route path="/cadastro-usuario" element={<CadastroUsuario/>}></Route>
            </Routes>
        </HashRouter>
    )
}
export default Rotas