import React from "react";
import {withRouter} from 'react-router-dom'
import Card from "../components/card";
import FormGroup from "../components/formgroup";
class ConsultaLancamentos extends React.Component{
    render(){
        return(
            <Card title="Consulta Lancamentos">
                <div className="row">
                    <div className="col-md-6">
                        <div className="bs-component">
                            <FormGroup label="Ano" htmlFor="inputAno">
                                    <input 
                                        type="text"
                                        class="form-control"
                                        id="inputAno"
                                        aria-describedby="emailHelp"
                                        placeholder="Digite o Ano"
                                        ></input>
                            </FormGroup>

                            <FormGroup label="Mês" htmlFor="inputMes">
                                    
                            </FormGroup>
                        </div>
                    </div>
                </div>
            </Card>
        )
    }
}

export default withRouter(ConsultaLancamentos);