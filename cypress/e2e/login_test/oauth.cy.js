Cypress.on('uncaught:exception', (err, runnable) => {
    return false;
});
describe('Load page', () => {
    it('Visits the LibreClinica', () => {
        cy.visit('http://localhost:8080/LibreClinica/pages/welcome')
        cy.contains('Oauth login')
    })
    it('Login with normal creds and go to dmm', () => {
        cy.visit('http://localhost:8080/LibreClinica/pages/welcome')
        cy.contains('Oauth login').should('exist').click()
        cy.origin('cdcoauthmockup.azurewebsites.net', () => {
            cy.url().should('contain', 'cdcoauthmockup.azurewebsites.net')
            cy.contains('OAuth Login')
            cy.get('[id="username"]').should('exist').type('root')
            cy.get('[id="password"]').should('exist').type(`${"test"}{enter}`)
        })
        cy.url().should('include', 'http://localhost:8080/LibreClinica/MainMenu')
        cy.contains('Welcome to')
        cy.contains('Drug Management').click()
    })
});