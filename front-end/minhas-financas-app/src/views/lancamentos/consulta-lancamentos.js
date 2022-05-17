import React from "react";
import {withRouter} from 'react-router-dom'
import Card from "../../components/card";
import FormGroup from "../../components/formgroup";
import SelectMenu from "../../components/selectMenu";
import LancamentosTable from "./lancamentosTable";

class ConsultaLancamentos extends React.Component{
    state = {
        ano: '',
        mes: '',
        tipo: ''
    }

    buscar = () => {
        console.log(this.state)
    }

    render(){
        const meses = [
            {label: 'Selecione...', value: ''},
            {label: 'Janeiro', value: 1},
            {label: 'Fevereiro', value: 2},
            {label: 'Março', value: 3},
            {label: 'Abril', value: 4},
            {label: 'Maio', value: 5},
            {label: 'Junho', value: 6},
            {label: 'Julho', value: 7},
            {label: 'Agosto', value: 8},
            {label: 'Setembro', value: 9},
            {label: 'Outubro', value: 10},
            {label: 'Novembro', value: 11},
            {label: 'Dezembro', value: 12},
        ]

        const tipos = [
            {label: 'Selecione...', value: ''},
            {label: 'Despesa', value: 'DESPESA'},
            {label: 'Receita', value: 'RECEITA'},
        ]
        const lancamentos = [
            {id: 1, descricao: 'Salário', valor: 5000, mes: 1, tipo : 'Receita', status: 'Efetivado'}
        ]
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
                                    <SelectMenu className="form-control" 
                                        value={this.state.mes}
                                        onChange={e => this.setState({mes: this.target.value})}
                                    lista={meses}/>
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
                            <LancamentosTable lancamentos={lancamentos}></LancamentosTable>
                        </div>
                    </div>
                </div>
            </Card>
        )
    }
}

export default withRouter(ConsultaLancamentos);