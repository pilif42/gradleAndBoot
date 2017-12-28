package com.example.gradleAndBoot.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * For this tutorial, for simplicity, we have made this bean a simple POJO.
 *
 * In a real project, it would most probably be an Entity bean: see all the commented out code below.
 */

//@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@Table(name = "request", schema = "mysvc")
public class Request implements Serializable {

  private static final long serialVersionUID = 6034836141646834386L;

//  @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "caseeventseq_gen")
//  @GenericGenerator(name = "caseeventseq_gen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
//      parameters = {
//          @Parameter(name = "sequence_name", value = "casesvc.caseeventseq"),
//          @Parameter(name = "increment_size", value = "1")})
//  @Column(name = "caseeventpk")
//  private Integer caseEventPK;

//  @Column(name = "casefk")
//  private Integer caseFK;

  private String title;
  private String forename;
}