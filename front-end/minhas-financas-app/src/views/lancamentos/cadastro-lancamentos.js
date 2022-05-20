import React from "react";
import { withRouter } from "react-router-dom";
import Card from "../../components/card";
import FormGroup from "../../components/formgroup";
import LancamentoService from "../../app/service/lancamentoService";
import SelectMenu from "../../components/selectMenu";
import * as messages from '../../components/toastr'
import LocalStorageService from "../../app/service/localStorageService";
class CadastroLancamento extends React.Component {

    state = {
        id: null,
        descricao: '',
        valor: '',
        mes: '',
        ano: '',
        tipo: '',
        status: ''
    }

    constructor(){
        super()
        this.service = new LancamentoService();
    }

    submit =() => {
        const usuarioLogado = LocalStorageService.obterItem("_usuarioLogado")
        
        const { descricao, valor, mes, ano, tipo } = this.state;
        const lancamento = {
            descricao,
            valor,
            mes,
            ano,
            tipo,
            usuario: usuarioLogado.id
        }

        this.service.salvar(lancamento)
                .then(response => {
                   
                    messages.mensagemSucesso('Lançamento cadastrado com sucesso')
                    this.changePage('/consulta-lancamentos', 4000)
                }).catch(err => {
                    messages.mensagemErro(err.response.data)
                })
    }

    changePage = (away, time) => {
        this.props.history.push(away)
        setTimeout(e => {
            window.location.reload();

        },time ) 
    }

    handleChange = (event) => {
        const value = event.target.value;
        const name = event.target.name;

        this.setState({[name] : value})
    }
    render() {
        
        
        const tipos = this.service.obterListTipos();
        const meses = this.service.opterListaMeses();
        
        
        return (

        

            <Card title="Cadastro de Lançamento">
                <div className="row">
                    <div className="col-md-12">
                        <FormGroup id="inputDescricao" label="Descrição">
                            <input id="inputDescricao" 
                                    type="text" 
                                    className="form-control" 
                                    name="descricao"
                                    value={this.state.descricao}
                                    onChange={this.handleChange} />
                        </FormGroup>
                    </div>
                </div>
                <div className="row">
                    <div className="col-md-6">
                        <FormGroup id="inputAno" label="Ano: *">
                            <input id="inputAno" 
                                   type="text" 
                                   name="ano"
                                   className="form-control"
                                   value={this.state.ano} 
                                   onChange={this.handleChange}/>
                        </FormGroup>
                    </div>
                    <div className="col-md-6">
                        <FormGroup id="inputMes" label="Mês: *">
                            <SelectMenu id="inputMes" 
                                        lista={meses} 
                                        name="mes"
                                        className="form-control"
                                        value={this.state.mes}
                                        onChange={this.handleChange}/>
                        </FormGroup>
                    </div>
                </div>
                <div className="row">
                    <div className="col-md-4">
                        <FormGroup id="inputValor" label="Valor: *">
                            <input id="inputValor" 
                                    type="text" 
                                    className="form-control" 
                                    value={this.state.valor}
                                    name="valor"
                                    onChange={this.handleChange}/>
                        </FormGroup>
                    </div>

                    <div className="col-md-4">
                        <FormGroup id="inputTipo" label="Tipo: *">

                            <SelectMenu id="inputTipo" 
                                        lista={tipos} 
                                        className="form-control"
                                        name="tipo"
                                        value={this.tipo}
                                        onChange={this.handleChange}></SelectMenu>
                        </FormGroup>
                    </div>

                    <div className="col-md-4">
                        <FormGroup id="inputStatus" label="Status: ">

                            <input type="text" 
                                    className="form-control" 
                                    value={this.state.status}
                                    name="status"
                                    disabled ></input>
                        </FormGroup>
                    </div>
                    
                    <div className="row">
                        
                        <div className="col-md-6">
                        <br></br>
                            <button className="btn btn-success" onClick={this.submit}>Salvar</button>
                            <button onClick={e => this.changePage('/consulta-lancamentos',0)} className="btn btn-danger">Cancelar</button>
                        </div>
                    </div>
                </div>
            </Card>
        )
    }
}

export default withRouter(CadastroLancamento)