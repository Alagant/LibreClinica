Cypress.on('uncaught:exception', (err, runnable) => {
    return false;
});
describe('Load page', () => {
    it('Visits the LibreClinica', () => {
        cy.visit('http://localhost:8080/LibreClinica/pages/login/login')
        cy.contains('Login')
    })
    it('Login with normal creds and go to dmm', () => {
        cy.visit('http://localhost:8080/LibreClinica/pages/login/login')
        cy.contains('OAuth Login').should('exist').click()
        cy.origin('cdcoauthmockup.azurewebsites.net', () => {
            cy.url().should('contain', 'cdcoauthmockup.azurewebsites.net')
            cy.contains('SAMS login mockup')
            cy.get('[id="username"]').should('exist').type('test')
            cy.get('[id="password"]').should('exist').type(`${"test"}{enter}`)
        })
        cy.url().should('include', 'http://localhost:8080/LibreClinica/MainMenu')
        cy.contains('Welcome to')
        cy.contains('Drug Management').click()
    })
});