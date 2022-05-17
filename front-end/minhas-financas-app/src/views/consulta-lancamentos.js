import React from "react";
import {withRouter} from 'react-router-dom'
import Card from "../components/card";
import FormGroup from "../components/formgroup";
import SelectMenu from "../components/selectMenu";

class ConsultaLancamentos extends React.Component{
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
                                        aria-describedby="emailHelp"
                                        placeholder="Digite o Ano"
                                        ></input>
                            </FormGroup>

                            <FormGroup label="Mês" htmlFor="inputMes">
                                    <SelectMenu className="form-control" lista={meses}/>
                            </FormGroup>
                            <FormGroup label="Tipo" htmlFor="inputTipo">
                                    <SelectMenu id="inputTipo" className="form-control" lista={tipos}/>
                            </FormGroup>
                        </div>
                    </div>
                </div>
            </Card>
        )
    }
}

export default withRouter(ConsultaLancamentos);