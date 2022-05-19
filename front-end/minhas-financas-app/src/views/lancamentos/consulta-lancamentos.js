import React from "react";
import {withRouter} from 'react-router-dom'
import Card from "../../components/card";
import FormGroup from "../../components/formgroup";
import SelectMenu from "../../components/selectMenu";
import LancamentosTable from "./lancamentosTable";
import LancamentoService from "../../app/service/lancamentoService";
import LocalStorageService from "../../app/service/localStorageService";
import * as message from '../../components/toastr'

class ConsultaLancamentos extends React.Component{
    state = {
        ano: '',
        mes: '',
        tipo: '',
        descricao: '',
        lancamentos: []
    }
    constructor(){
        super()
        this.service = new LancamentoService()
    }

    buscar = () => {
        if (!this.state.ano) {
            message.mensagemErro('O preenchimento do campo ano é obrigatório.')
            return false;
        }
        const usuarioLogado = LocalStorageService.obterItem('_usuarioLogado')
        const lancamentoFiltro = {
            ano: this.state.ano,
            mes: this.state.mes,
            tipo: this.state.tipo,
            descricao: this.state.descricao,
            usuario: usuarioLogado.id
        }

        this.service.consultar(lancamentoFiltro)
            .then(response => {
                console.log(response)
                this.setState({lancamentos: response.data})
            }).catch(err => {
                console.log(err)
            })
    }

    render(){
        const meses = this.service.opterListaMeses();
        const tipos = this.service.obterListTipos();

        return(
            <Card title="Consulta Lancamentos">
                <div className="row">
                    <div className="col-md-6">
                        <div className="bs-component">
                            <FormGroup label="Ano" htmlFor="inputAno">
                                    <input 
                                        type="text"
                                        className="form-control"
                                        id="inputAno"
                                        value={this.state.ano}
                                        onChange={e => this.setState({ano: e.target.value})}
                                        placeholder="Digite o Ano"
                                        ></input>
                            </FormGroup>

                            <FormGroup label="Mês" htmlFor="inputMes">
                                    <SelectMenu id="inputMes" 
                                                className="form-control" 
                                                value={this.state.mes}
                                                onChange={e => this.setState({mes: this.target.value})}
                                                lista={meses}/>
                            </FormGroup>


                            <FormGroup label="Descrição" htmlFor="inputDesc">
                                    <input 
                                        type="text"
                                        className="form-control"
                                        id="inputDescricao"
                                        value={this.state.descricao}
                                        onChange={e => this.setState({descricao: e.target.value})}
                                        placeholder="Digite a descrição"
                                        ></input>
                            </FormGroup>


                            <FormGroup label="Tipo Lancamento" htmlFor="inputTipo">
                                    <SelectMenu id="inputTipo"
                                    value={this.state.tipo}
                                    onChange={e => this.setState({tipo : this.target.value})}
                                    className="form-control" lista={tipos}/>
                            </FormGroup>

                                <button onClick={this.buscar} type="button" className="btn btn-success">Buscar</button>
                                <button type="button" className="btn btn-danger">Cadastrar</button>
                        </div>
                    </div>
                </div>
                <br></br>
                <div className="row">
                    <div className="col-md-12">
                        <div className="bs-component">
                            <LancamentosTable lancamentos={this.state.lancamentos}></LancamentosTable>
                        </div>
                    </div>
                </div>
            </Card>
        )
    }
}

export default withRouter(ConsultaLancamentos);