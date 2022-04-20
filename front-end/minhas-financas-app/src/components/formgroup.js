import React from "react";

function FormGroup(props) {
    return (
        <div className="form-group">
            <label htmlFor={props.htmlFor} style={{marginBottom: '10px', marginTop: '10px' }}>{props.label}</label>
            {props.children}
        </div>
    )
}

export default FormGroup;