import React from "react";
import { Company } from "core/types/Company";
import './styles.scss'

interface Props {
    company: Company;
  }
  
  const CompanyCard = ({ company }: Props) => {
    return (
      <div>
        <h2>{company.name}</h2>
        <p>{company.description}</p>
      </div>
    );
  };
  
  export default CompanyCard;