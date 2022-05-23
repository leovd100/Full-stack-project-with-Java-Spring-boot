import React from "react";
import Login from "../views/login";
import CadastroUsuario from "../views/cadastrUsuario";
import { Route, Switch  , BrowserRouter} from 'react-router-dom'
import Home from "../views/home";
import consultaLancamentos from "../views/lancamentos/consulta-lancamentos";
import cadastroLancamentos from "../views/lancamentos/cadastro-lancamentos";
function Rotas(){
    return(
        <BrowserRouter>
            <Switch>
                <Route path="/home" component={Home}/>
                <Route path="/login" component={Login}/>
                <Route path="/cadastro-usuario" component={CadastroUsuario}/>
                <Route path="/consulta-lancamentos" component={consultaLancamentos}/>
                <Route path="/cadastro-lancamentos/:id?" component={cadastroLancamentos}/>
            </Switch>
        </BrowserRouter>
    )
}
export default Rotas