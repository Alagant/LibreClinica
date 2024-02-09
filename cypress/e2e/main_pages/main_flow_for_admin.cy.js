Cypress.on('uncaught:exception', (err, runnable) => {
    return false;
});

describe('Load page', () => {
    it('Visits the LibreClinica', () => {
        cy.visit('http://localhost:8080/LibreClinica/pages/login/login')
        cy.contains('Login')
    })
    it('Login and navegate for main pages', () => {
        cy.visit('http://localhost:8080/LibreClinica/pages/login/login')
        cy.url().should('include', 'http://localhost:8080/LibreClinica/pages/login/login')
        cy.get('[id="username"]').should('exist').type('root')
        cy.get('[id="j_password"]').should('exist').type('kevin12345')
        cy.get('[name="submit"]').should('exist').click()
        cy.url().should('include', 'http://localhost:8080/LibreClinica/MainMenu')
        cy.contains('Welcome to')
        cy.contains('Home').click()
        cy.contains('Subject Matrix').click()
        cy.contains('Notes & Discrepancies').click()
        cy.contains('Study Audit Log').click()
        cy.contains('Tasks').trigger('mouseover')
    })
});