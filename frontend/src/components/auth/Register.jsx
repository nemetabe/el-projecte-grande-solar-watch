import React from "react";
import FormInput from "../common/FormInput";

function Register({ handleSubmit, handleChange, formData }) {
  return (
    <div className="form-container sign-up-container">
      <form onSubmit={handleSubmit}>
        <h5>Create an account</h5>
        <span>or fill out this to register</span>

        <FormInput
          id="username"
          name="username"
          placeholder="Username"
          value={formData.username}
          onChange={handleChange}
        />

        <FormInput
          id="email"
          name="email"
          type="email"
          placeholder="Email"
          value={formData.email}
          onChange={handleChange}
        />

        <FormInput
          id="favCity"
          name="favCity"
          placeholder="City"
          value={formData.favCity}
          onChange={handleChange}
        />

        <FormInput
          id="password"
          name="password"
          type="password"
          placeholder="Password"
          value={formData.password}
          onChange={handleChange}
        />

        <button>Sign Up</button>
      </form>
    </div>
  );
}

export default Register;