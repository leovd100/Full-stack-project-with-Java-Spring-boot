import React from 'react'
import Card from '../components/card'
import FormGroup from '../components/formgroup'
import { withRouter } from 'react-router-dom'
import UsuarioService from '../app/service/usuarioService'
import {mensagemSucesso, mensagemErro} from '../components/toastr'
class CadastroUsuario extends React.Component {

    state = {
        nome: '',
        email: '',
        senha: '',
        senhaRepeticao: ''
    }

    validar(){
        const msgs = []
        if (!this.state.nome){
            msgs.push('O campo nome é obrigatório.')
        }
        if (!this.state.email){
            msgs.push('O campo Email é obrigatório')
        }else if(this.state.email.match(/^[a-z0-9]+@[a-z0-9]+\.[a-z]/)){
            msgs.push('Informe um Email válido')
        }

        if(!this.state.senha || !this.state.senhaRepeticao){
            msgs.push('Digite a senha 2x')
        }else if(this.state.senha !== this.state.senhaRepeticao){
            msgs.push('As senhas não batem')
        }



        return msgs;
    }


    constructor(){
        super()
        this.service = new UsuarioService()
    }

    cadastrar = () => {
        const msgs = this.validar();

        if(msgs && msgs.length > 0){
            msgs.forEach( (msg, index) => {
                mensagemErro(msg)
            });
            return false
        }

        const usuario = {
            nome: this.state.nome,
            email: this.state.email,
            senha: this.state.senha
        }
        this.service.salvar(usuario)
            .then(response => {
                mensagemSucesso('Usuario cadastrado com sucesso! Faça o login para acessar o sistema.')
                
                setTimeout(x => {
                    this.props.history.push('/login')
                    window.location.reload();
                }, 5000)
                

            }).catch(err=> {
                mensagemErro(err.response.data)
            })
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