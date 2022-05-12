import React from "react";
import Login from "../views/login";
import CadastroUsuario from "../views/cadastrUsuario";
import { Route, Routes ,BrowserRouter } from 'react-router-dom'

function Rotas(){
    return(
        <BrowserRouter>
            <Routes>
                <Route path="/login" element={<Login/>}></Route>
                <Route path="/cadastro-usuario" element={<CadastroUsuario/>}></Route>
            </Routes>
        </BrowserRouter>
    )
}
export default Rotas