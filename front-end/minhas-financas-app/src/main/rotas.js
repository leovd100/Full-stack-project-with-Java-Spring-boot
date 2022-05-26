import React from "react";
import Login from "../views/login";
import CadastroUsuario from "../views/cadastrUsuario";
import { Route, Switch  , BrowserRouter, Redirect} from 'react-router-dom'
import Home from "../views/home";
import consultaLancamentos from "../views/lancamentos/consulta-lancamentos";
import cadastroLancamentos from "../views/lancamentos/cadastro-lancamentos";
import {AuthCosumer} from './ProvedorAutenticacao'


function RotaAutenticada({component: Component, isUsuarioAutenticado ,...props}){
    return (
        <Route {...props} render={(componentProps) => {
    
            if(isUsuarioAutenticado){
                return(
                    <Component {...componentProps}/>
                )
            }else {
                return(
                    <Redirect to={{pathname: '/login', state: {from: componentProps.location}}}/>
                )
            }
        }}></Route>
    )
}

function Rotas(props){
    return(
        <BrowserRouter>
            <Switch>
                <Route path="/login" component={Login}/>
                <Route path="/cadastro-usuario" component={CadastroUsuario}/>
                
                <RotaAutenticada isUsuarioAutenticado={props.isUsuarioAutenticado} path="/home" component={Home}/>
                <RotaAutenticada isUsuarioAutenticado={props.isUsuarioAutenticado} path="/consulta-lancamentos" component={consultaLancamentos}/>
                <RotaAutenticada isUsuarioAutenticado={props.isUsuarioAutenticado} path="/cadastro-lancamentos/:id?" component={cadastroLancamentos}/>
            </Switch>
        </BrowserRouter>
    )
}

const auth = () =>{
    return (
        <AuthCosumer>
         {   (context) => {
       
             return (<Rotas isUsuarioAutenticado={context.isAutenticado} />) }}
        </AuthCosumer>
    )
}

export default auth