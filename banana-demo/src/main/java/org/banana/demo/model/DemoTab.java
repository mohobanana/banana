package org.banana.demo.model;

public class DemoTab {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column demo_tab.id
     *
     * @mbg.generated Wed Dec 15 18:09:42 CST 2021
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column demo_tab.name
     *
     * @mbg.generated Wed Dec 15 18:09:42 CST 2021
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column demo_tab.number
     *
     * @mbg.generated Wed Dec 15 18:09:42 CST 2021
     */
    private Integer number;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column demo_tab.id
     *
     * @return the value of demo_tab.id
     *
     * @mbg.generated Wed Dec 15 18:09:42 CST 2021
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column demo_tab.id
     *
     * @param id the value for demo_tab.id
     *
     * @mbg.generated Wed Dec 15 18:09:42 CST 2021
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column demo_tab.name
     *
     * @return the value of demo_tab.name
     *
     * @mbg.generated Wed Dec 15 18:09:42 CST 2021
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column demo_tab.name
     *
     * @param name the value for demo_tab.name
     *
     * @mbg.generated Wed Dec 15 18:09:42 CST 2021
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column demo_tab.number
     *
     * @return the value of demo_tab.number
     *
     * @mbg.generated Wed Dec 15 18:09:42 CST 2021
     */
    public Integer getNumber() {
        return number;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column demo_tab.number
     *
     * @param number the value for demo_tab.number
     *
     * @mbg.generated Wed Dec 15 18:09:42 CST 2021
     */
    public void setNumber(Integer number) {
        this.number = number;
    }
}