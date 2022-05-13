import React from "react";
import Card from '../components/card'
import FormGroup from "../components/formgroup";
import { withRouter } from 'react-router-dom'
import axios from 'axios'
class Login extends React.Component {

    state = {
        email: '',
        senha: ''
    }

    entrar = () => {

        axios.post('http://localhost:8080/api/usuarios/autenticar', {
            email : this.state.email,
            senha:  this.state.senha
        }).then(response => console.log(response)).catch(err => console.log(err.response))

    }

    prepareCadastrar = () => {
        this.props.history.push("/cadastro-usuario")
        window.location.reload();
    }

    render() {
        return (
          
                <div className="row">
                    <div className="col-md-6" style={{ position: 'relative', left: '300px' }}>
                        <div className="bs-docs-section">
                            <Card title="Login">
                                <div className="row">
                                    <div className="col-lg-12">
                                        <div className="bs-component">
                                            <fieldset>
                                               <FormGroup label="Email: *" htmlFor="exampleInputEmail1">
                                                    <input type="email" className="form-control" 
                                                                        id="exampleInputEmail1" 
                                                                        aria-describedby="emailHelp" 
                                                                        placeholder="Digite o Email"
                                                                        value={this.state.emai}
                                                                        onChange={e => this.setState({ email:e.target.value})}/>
                                               </FormGroup>
                                               <FormGroup label="Senha: *" htmlFor="exampleInputPassword1">
                                                    <input type="password" className="form-control" 
                                                                           id="exampleInputPassword1" 
                                                                           placeholder="Password" 
                                                                           value={this.state.senha}
                                                                           onChange={e => this.setState({senha: e.target.value})}/>
                                               </FormGroup>
                                               <button onClick={this.entrar} className="btn btn-success" style={{marginTop: '1rem'}}>Entrar</button>
                                               <button className="btn btn-danger" onClick={this.prepareCadastrar} style={{marginTop: '1rem'}}>Cadastrar</button>
                                            </fieldset>
                                        </div>
                                    </div>
                                </div>
                            </Card>
                        </div>
                    </div>
                </div>
         
        )
    }
}

export default  withRouter( Login ) ; 