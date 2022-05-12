import React from 'react'
import Card from '../components/card'
import FormGroup from '../components/formgroup'
import { withRouter } from 'react-router-dom'
class CadastroUsuario extends React.Component {

    state = {
        nome: '',
        email: '',
        senha: '',
        senhaRepeticao: ''
    }

    cadastrar = () => {
        console.log(this.state)
    }

    cancelar = () => {
        this.props.history.push("/login")
        window.location.reload();
    }

    render() {
        return (
            <Card title="Cadastro de Usuário">

                <div className="row">
                    <div className="col-lg-12">
                        <div className="bs-component">
                            <FormGroup label="Nome: *" htmlFor="inputNome">
                                <input type="text"
                                    className='form-control'
                                    id="inputNome"
                                    name="nome"
                                    onChange={e => this.setState({ nome: e.target.value })}></input>
                            </FormGroup>
                            <FormGroup label="Email: *">
                                <input type="email"
                                    htmlFor="inputEmail"
                                    className='form-control'
                                    id="inputEmail"
                                    name="email"
                                    onChange={e => this.setState({ email: e.target.value })}></input>
                            </FormGroup>
                            <FormGroup label="Senha: *">
                                <input type="password"
                                    htmlFor="inputSenha"
                                    className='form-control'
                                    id="inputSenha"
                                    name="senha"
                                    onChange={e => this.setState({ senha: e.target.value })}></input>
                            </FormGroup>
                            <FormGroup label="Repita a senha: *">
                                <input type="password"
                                    htmlFor="inputRepitaSenha"
                                    className='form-control'
                                    id="inputRepitaSenha"
                                    name="senha"
                                    onChange={e => this.setState({ senhaRepeticao: e.target.value })}></input>
                            </FormGroup>
                            <button onClick={this.cadastrar} type="button" className='btn btn-success'>Salvar</button>
                            <button onClick={this.cancelar} type="button" className='btn btn-danger'>Cancelar</button>
                        </div>
                    </div>
                </div>
                
            </Card>
        )
    }
}

export default withRouter(CadastroUsuario)